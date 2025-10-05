import cv2
import numpy as np

def load_image_normalized(filename):
    """Load and normalize image to [0,1] range."""
    img = cv2.imread(filename, cv2.IMREAD_GRAYSCALE)
    if img is None:
        raise FileNotFoundError(f"Image file not found: {filename}")
    
    # Normalize to [0,1] range
    img_float = img.astype(np.float32)
    max_val = img_float.max()
    return img_float / max_val if max_val > 0 else img_float


def save_gif(frames, filename="animation.gif", duration=100):
    """Save list of images as animated GIF."""
    if not frames:
        raise ValueError("No frames provided for GIF creation")

    try:
        from PIL import Image
    except ImportError:
        raise ImportError("PIL (Pillow) is required for GIF generation")
    
    # Convert frames to uint8 format (0-255) more efficiently
    processed_frames = []
    for frame in frames:
        if frame.dtype != np.uint8:
            frame_normalized = np.clip(frame, 0, 1) if frame.max() <= 1.0 else np.clip(frame / 255.0, 0, 1)
            frame_uint8 = (frame_normalized * 255).astype(np.uint8)
        else:
            frame_uint8 = frame
        processed_frames.append(frame_uint8)
    
    # Convert to PIL Images and save
    pil_frames = [Image.fromarray(frame) for frame in processed_frames]
    pil_frames[0].save(
        filename,
        save_all=True,
        append_images=pil_frames[1:],
        duration=duration,
        loop=0,
        optimize=True
    )
