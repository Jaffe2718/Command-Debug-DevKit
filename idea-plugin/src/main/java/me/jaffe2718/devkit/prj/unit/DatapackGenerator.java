package me.jaffe2718.devkit.prj.unit;

import com.intellij.openapi.project.Project;
import net.lingala.zip4j.ZipFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class DatapackGenerator {
    private final Project project;

    public DatapackGenerator(Project project) {
        this.project = project;
    }

    public CheckState generate() {
        Path prjRoot = Path.of(Objects.requireNonNull(this.project.getBasePath()));  // find the project's root dir
        File packMetaPath = prjRoot.resolve("pack.mcmeta").toFile();   // find the pack.mcmeta file
        File dataDirPath = prjRoot.resolve("data").toFile();           // find `data` dir
        File packPngPath = prjRoot.resolve("pack.png").toFile();       // find pack.png
        Path buildDirPath = prjRoot.resolve("build");                  // find `build` dir
        String pkName = this.project.getName() + ".zip";                     // the name of the zip file

        if (!packMetaPath.exists() || !dataDirPath.exists()) {
            return CheckState.INVALID;
        }
        try {
            ZipFile datapack = new ZipFile(buildDirPath.resolve(pkName).toFile());
            datapack.addFile(packMetaPath);
            datapack.addFolder(dataDirPath);
            if (packPngPath.exists()) {
                datapack.addFile(packPngPath);
            } else {
                datapack.close();
                return CheckState.VALID_WITH_WARNINGS;
            }
        } catch (IOException e) {
            return CheckState.INVALID;
        }
        return CheckState.VALID;
    }

}
