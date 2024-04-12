package model;

@lombok.Data
public class Data {
    private int id;
    private String fileName;
    private String path;
    private String email;

    public Data(int id, String fileName, String path) {
        this.id = id;
        this.fileName = fileName;
        this.path = path;
    }

    public Data(String fileName, String path, String email) {
        this.fileName = fileName;
        this.path = path;
        this.email = email;
    }
}
