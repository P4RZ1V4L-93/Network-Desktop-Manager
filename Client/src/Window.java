public enum Window {
    ScreenShare(-10),
    Chat(-11),
    Menu(-12);

    private int value;
    Window(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
