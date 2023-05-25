package tracker;


public class Main {

    public static void main(String[] args) {
        StudentsDAO studentsDAO = new StudentsDAO();

        TUIHandler tuiHandler = new TUIHandler(studentsDAO);
        tuiHandler.run();
    }
}
