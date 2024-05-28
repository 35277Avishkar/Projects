import os
import numpy as np
import pandas as pd
import tensorflow as tf
from tensorflow.keras.models import Sequential, Model
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, Dropout, GlobalAveragePooling2D
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from tensorflow.keras.applications import EfficientNetB0
from PIL import Image

# Constants
IMG_SIZE = 224  # EfficientNet requires a larger input size
BATCH_SIZE = 32
EPOCHS = 25

# Load dataset
data_dir = 'path_to_dataset/HAM10000_images'
metadata = pd.read_csv('path_to_metadata/HAM10000_metadata.csv')

# Preprocess images
def preprocess_image(img_path):
    img = Image.open(img_path)
    img = img.resize((IMG_SIZE, IMG_SIZE))
    img = np.array(img) / 255.0
    return img

# Load images and labels
images = []
labels = []
for idx, row in metadata.iterrows():
    img_path = os.path.join(data_dir, row['image_id'] + '.jpg')
    img = preprocess_image(img_path)
    images.append(img)
    labels.append(row['dx'])

images = np.array(images)
labels = np.array(labels)

# Full names for labels
full_labels = {
    'akiec': 'Actinic keratoses',
    'bcc': 'Basal cell carcinoma',
    'bkl': 'Benign keratosis-like lesions',
    'df': 'Dermatofibroma',
    'mel': 'Melanoma',
    'nv': 'Melanocytic nevi',
    'vasc': 'Vascular lesions'
}

# Map short labels to full names
labels = np.array([full_labels[label] for label in labels])

# Encode labels
label_encoder = LabelEncoder()
labels_encoded = label_encoder.fit_transform(labels)
labels_encoded = tf.keras.utils.to_categorical(labels_encoded, num_classes=7)

# Train-test split
X_train, X_test, y_train, y_test = train_test_split(images, labels_encoded, test_size=0.2, random_state=42)

# Use EfficientNetB0 as the base model
base_model = EfficientNetB0(weights='imagenet', include_top=False, input_shape=(IMG_SIZE, IMG_SIZE, 3))

# Add custom layers on top of the base model
x = base_model.output
x = GlobalAveragePooling2D()(x)
x = Dense(128, activation='relu')(x)
x = Dropout(0.5)(x)
predictions = Dense(7, activation='softmax')(x)

model = Model(inputs=base_model.input, outputs=predictions)

# Freeze the base model layers
for layer in base_model.layers:
    layer.trainable = False

model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

# Data augmentation
datagen = ImageDataGenerator(
    rotation_range=20,
    width_shift_range=0.2,
    height_shift_range=0.2,
    horizontal_flip=True,
    validation_split=0.2
)

# Fit the model
history = model.fit(datagen.flow(X_train, y_train, batch_size=BATCH_SIZE, subset='training'),
                    validation_data=datagen.flow(X_train, y_train, batch_size=BATCH_SIZE, subset='validation'),
                    epochs=EPOCHS)

# Unfreeze some layers and fine-tune the model
for layer in base_model.layers[-20:]:
    layer.trainable = True

model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=1e-5), loss='categorical_crossentropy', metrics=['accuracy'])

history_fine_tune = model.fit(datagen.flow(X_train, y_train, batch_size=BATCH_SIZE, subset='training'),
                              validation_data=datagen.flow(X_train, y_train, batch_size=BATCH_SIZE, subset='validation'),
                              epochs=EPOCHS)

# Save the model
model.save('skin_disease_model.h5')
