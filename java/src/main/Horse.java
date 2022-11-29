package main;

public class Horse {
    /**
     * 馬名
     */
    private String name;
    
    /**
     * 性別
     * 牡馬，牝馬，騙馬の3種
     */
    private String sex;

    /**
     * 年齢
     */
    private int age;

    /**
     * 馬体重
     * XXX.Xkg
     */
    private double weight;
    
    /**
     * オッズ
     * 0.0倍
     */
    private String odds;

    /**
     * 騎手
     */
    private String jockey;

    /**
     * 斤量
     * XX.0kg
     */
    private double toCarry;

    /**
     * 馬番
     * 1 <= x <= 18
     */
    private int number;

    /**
     * 馬別Net競馬URL
     */
    private String url;
    
    public Horse() {
        this.jockey = "";
        this.name = "";
        this.sex = "";
        this.url = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getJockey() {
        return jockey;
    }

    public void setJockey(String jockey) {
        this.jockey = jockey;
    }

    public double getToCarry() {
        return toCarry;
    }

    public void setToCarry(double toCarry) {
        this.toCarry = toCarry;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String toString() {
        return String.format("%-10s%-5s", this.getName(), this.getOdds());
        
    }
}
