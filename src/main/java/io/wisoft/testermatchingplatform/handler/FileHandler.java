package io.wisoft.testermatchingplatform.handler;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.UUID;

public class FileHandler {

    private static final String DEFAULT_PROFILE_FILE_PATH = System.getProperty("user.dir") + "/profile/";
    private static final String DEFAULT_APPLY_REQUIRE_FILE_PATH = System.getProperty("user.dir") + "/auth/require/";
    private static final String DEFAULT_APPLY_PREFER_FILE_PATH = System.getProperty("user.dir") + "/auth/prefer/";

    static {
        final File defaultProfileFileSaveDirectory = new File(DEFAULT_PROFILE_FILE_PATH);
        final File defaultAuthRequireFileSaveDirectory = new File(DEFAULT_APPLY_REQUIRE_FILE_PATH);
        final File defaultAuthPreferFileSaveDirectory = new File(DEFAULT_APPLY_PREFER_FILE_PATH);

        if (!defaultProfileFileSaveDirectory.exists()) {
            defaultProfileFileSaveDirectory.mkdir();
        }
        if (!defaultAuthRequireFileSaveDirectory.exists()) {
            defaultAuthRequireFileSaveDirectory.mkdirs();
        }
        if (!defaultAuthPreferFileSaveDirectory.exists()) {
            defaultAuthPreferFileSaveDirectory.mkdirs();
        }


    }

    public static String saveProfileFileData(final MultipartFile fileData) {
        if (fileData.isEmpty()) {
            throw new IllegalArgumentException("프로필 이미지를 전송해야 합니다.");
        }

        final String extension = fileData
                .getContentType()
                .split("/")[1];

        final String fileName = UUID.randomUUID().toString();
        final File file = new File(DEFAULT_PROFILE_FILE_PATH + fileName + "." + extension);

        return saveFile(file, fileData);
    }

    public static byte[] getProfileFileData(final String fileName) {
        try {
            final File file = new File(DEFAULT_PROFILE_FILE_PATH + fileName);

            return Files.readAllBytes(file.toPath());
        } catch (NoSuchFileException e) {
            System.err.println("No file " + fileName);
            throw new IllegalArgumentException("존재하지 않는 파일명입니다.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new IllegalStateException("파일을 읽어오는 데 실패하였습니다.");
        }
    }

    public static String saveApplyRequireFileData(final MultipartFile fileData) {
        if (fileData.isEmpty()) {
            return null;
        }

        final String extension = fileData
                .getContentType()
                .split("/")[1];

        final String fileName = UUID.randomUUID().toString();
        final File file = new File(DEFAULT_APPLY_REQUIRE_FILE_PATH + fileName + "." + extension);

        return saveFile(file, fileData);
    }

    public static byte[] getApplyRequireFileData(final String fileName) {
        try {
            final File file = new File(DEFAULT_APPLY_REQUIRE_FILE_PATH + fileName);

            return Files.readAllBytes(file.toPath());
        } catch (NoSuchFileException e) {
            System.err.println("No file " + fileName);
            throw new IllegalArgumentException("존재하지 않는 파일명입니다.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new IllegalStateException("파일을 읽어오는 데 실패하였습니다.");
        }
    }

    public static String saveApplyPreferFileData(final MultipartFile fileData) {
        if (fileData.isEmpty()) {
            return null;
        }

        final String extension = fileData
                .getContentType()
                .split("/")[1];

        final String fileName = UUID.randomUUID().toString();
        final File file = new File(DEFAULT_APPLY_PREFER_FILE_PATH + fileName + "." + extension);

        return saveFile(file, fileData);
    }

    public static byte[] getApplyPreferFileData(final String fileName) {
        try {
            final File file = new File(DEFAULT_APPLY_PREFER_FILE_PATH + fileName);

            return Files.readAllBytes(file.toPath());
        } catch (NoSuchFileException e) {
            System.err.println("No file " + fileName);
            throw new IllegalArgumentException("존재하지 않는 파일명입니다.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new IllegalStateException("파일을 읽어오는 데 실패하였습니다.");
        }
    }



    public static String getImageContentType(final String imageFileName) {
        final String[] splitFileName = imageFileName.split("[.]");
        if (splitFileName.length < 2 || splitFileName[1].isBlank()) {
            throw new IllegalArgumentException("파일 형식이 잘못되었습니다.");
        }

        final String extension = imageFileName.split("[.]")[1];

        return "image/" + extension;
    }

    private static String saveFile(final File file, final MultipartFile fileData) {
        try {
            fileData.transferTo(file);
            return file.getName();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new IllegalStateException("파일을 저장하는 데 실패하였습니다.");
        }
    }

    public static String getFileName(final String filePath) {
        final String[] splitFilePath = filePath.split("/");

        return splitFilePath[splitFilePath.length - 1];
    }
}
