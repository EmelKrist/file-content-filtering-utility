public class Main {
    public static void main(String[] args) {
        System.setProperty("console.encoding", "UTF-8");
        FileContentFilteringUtility utility = new FileContentFilteringUtility();
        utility.run(args);
    }
}