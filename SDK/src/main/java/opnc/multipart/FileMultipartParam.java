package opnc.multipart;

import java.io.File;

/**
 * @desc:
 * @fileName: FileMultipartParam
 * @author: tangjiaxiang
 * @createTime: 2020/12/1 17:32:32
 * @modifier:
 */
public class FileMultipartParam implements MultipartParam {

    /**
     * 参数名
     */
    private String name;
    /**
     * 文件
     */
    private File file;
    /**
     * 文件名
     */
    private String filename;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
