package simplechat.core;

public class Message {
    private String contents;

    Message(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}
