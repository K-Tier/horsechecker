package main;

public class CheckId {
    /**
     * 探索するIDが存在するブロック
     */
    private String searchingArea;
    
    /**
     * 保持するID名
     */
    private String idName;
    
    /**
     * 探索するID名
     */
    private String wantedId;
    
    /**
     * 探索している値
     */
    private String value;
    
    /**
     * 探索目標のあるエリアに到達したか
     */
    private boolean flag;
    
    /**
     * コンストラクタ
     * @param searchId このインスタンスが探索するブロック
     * @param wantedId このインスタンスが探索するID名
     */
    public CheckId(String searchingArea, String wantedId) {
        this.searchingArea = searchingArea;
        this.idName = "";
        this.wantedId = wantedId;
        this.value = "";
        this.flag = false;
    }
    
    public String getSearchingArea() {
        return searchingArea;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getWantedId() {
        return wantedId;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * 探索するIDが存在するエリアか判断する
     * @param c Webサイトから読み取った文字
     * @return 目的のエリアに到達したか
     */
    public boolean searchArea(char c) {
        if(!this.flag) {
            if(c == this.searchingArea.charAt(this.value.length())) {
                setValue(this.value + c);
            } else {
                setValue("");
            }
            if(this.searchingArea.equals(this.value)) {
                setFlag(true);
                return flag;
            }
        }
        return flag;
    }

    /**
     * 探索目標のIDか判断する
     * @param i Webサイトから読み取った文字
     * @return 目的のIDに到達したか
     */
    public boolean searchIdName(char c) {
        if(this.flag) {
            if(c == this.wantedId.charAt(this.idName.length())) {
                setIdName(this.idName + c);
            } else {
                setIdName("");
            }
            if(this.idName.equals(this.wantedId)) {
                // System.out.println("探索成功");
                return true;
            }
        } else {
            searchArea(c);
        }
        return false;
    }
}