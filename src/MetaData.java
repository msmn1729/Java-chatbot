public class MetaData {
    public static final MetaData BANNER = new MetaData("b", "banner");
    private String data1, data2;

    public MetaData(String data1, String data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public boolean contain(String metaData) {
        return (metaData.equals(data1) || metaData.equals(data2));
    }
}
