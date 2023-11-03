package Classes.Theme;

/*
این کد یک کلاس `SoundEffect` را تعریف می‌کند که سه متد `wellcomeSound` و `errorSound` و `successSound`
 برای پخش افکت صوتی در برنامه  دارد.

در هر یک از متدها، با استفاده از کلاس `Paths`، مسیر فایل مورد نظر برای پخش صدا را تعیین می‌کند
 و سپس با استفاده از کلاس `AudioInputStream`، فایل صوتی را دریافت می‌کند.

سپس با استفاده از کلاس `Clip`، یک کلیپ جدید برای پخش صدا ایجاد می‌شود
و با استفاده از متد `open`، فایل صوتی به کلیپ اضافه می‌شود.

در نهایت با استفاده از متد `start`، پخش صدا آغاز می‌شود.

   `````````````````````````````````````````````````````

The code you provided defines a `SoundEffect` class that is responsible for playing different sound effects
using the Java Sound API (`javax.sound.sampled`).

Here's a breakdown of the code:

    - The class contains three methods: `wellcomeSound()`, `errorSound()`, and `successSound()`,
      each of which plays a different sound effect.
    - Each method follows a similar structure:
      - It starts by creating a `Path` object using the file path specified in
        the corresponding constant (`WellcomeSoundPath`, `ErrorSoundPath`, or `SuccessSoundPath`).
      - The `Path` object is then converted to an absolute path string using `toAbsolutePath().toString()`.
      - An `AudioInputStream` is obtained by calling `AudioSystem.getAudioInputStream()` and passing a `File` object
        created from the absolute path string.
      - A `Clip` object is created using `AudioSystem.getClip()`.
      - The `Clip` is opened by calling `clip.open(audioInputStream)`.
      - Finally, the sound effect is played by calling `clip.start()`.

To use this class, you need to provide the file paths for the sound effects by setting the corresponding constants
(`WellcomeSoundPath`, `ErrorSoundPath`, and `SuccessSoundPath`) to the appropriate file paths in your file system.

Note: This code assumes that the sound files specified by the file paths are in a supported audio format (e.g., WAV)
and that the Java Sound API is available and configured correctly on your system.
*/

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static Classes.Pathes.FilesPath.*;

public class SoundEffect {

    public static void wellcomeSound() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Path filePath = Paths.get(WellcomeSoundPath);
        String absolutePath = filePath.toAbsolutePath().toString();
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(absolutePath));

        Clip clip = AudioSystem.getClip();

        clip.open(audioInputStream);

        clip.start();
    }


    public static void errorSound() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Path filePath = Paths.get(ErrorSoundPath);
        String absolutePath = filePath.toAbsolutePath().toString();
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(absolutePath));

        Clip clip = AudioSystem.getClip();

        clip.open(audioInputStream);

        clip.start();
    }
    public static void successSound() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        Path filePath = Paths.get(SuccessSoundPath);
        String absolutePath = filePath.toAbsolutePath().toString();
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(absolutePath));

        Clip clip = AudioSystem.getClip();

        clip.open(audioInputStream);

        clip.start();
    }


}

//  1402/03/13 14:00 p.m. ~ 3 - 6 - 2023
