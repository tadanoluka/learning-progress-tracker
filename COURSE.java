package tracker;

public enum COURSE {

    JAVA("Java"),
    DSA("Data Structures and Algorithms (DSA)"),
    DATABASE("Databases"),
    SPRING("Spring");

    private String name;
    COURSE(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
