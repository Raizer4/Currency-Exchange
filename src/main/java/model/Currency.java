package model;

public class Currency {
    int id;
    String name;
    String code;
    String sign;

    public Currency(int id, String name, String code, String sign) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.sign = sign;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getSign() { return sign; }
    public void setSign(String sign) { this.sign = sign; }
}
