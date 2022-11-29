package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Race {
    /**
     * 重賞レースの特集ページのID
     */
    private String raceNumber;
    
    /**
     * 各レース固有のID
     */
    private String raceId;
    
    /**
     * レースに出場する競走馬のリスト
     */
    private ArrayList<Horse> horses;
    
    public Race(String raceNumber) {
        this.raceNumber = raceNumber;
        this.raceId = "";
        this.horses = new ArrayList<Horse>();
    }

    public String getRaceNumber() {
        return raceNumber;
    }

    public String getRaceId() throws IOException {
        if(this.raceId.isEmpty()) {
            searchRaceId();
        }
        return raceId;
    }

    public void setRaceId(String raceId) {
        this.raceId = raceId;
    }

    public ArrayList<Horse> getHorses() {
        return horses;
    }
    
    /**
     * raceIdから対象レースのhtmlファイルを読み取り出馬表の内容を抽出, horsesに入力する
     * @throws IOException ファイルの読み取り時にエラーが発生する可能性あり
     */
    public void setHorses() throws IOException {
        try {
            // 対象レースのWebページURL
            String url = "https://race.netkeiba.com/race/shutuba.html?race_id=" + this.raceId;
            // 出馬表を探すオブジェクト
            CheckPosition checkHorseList = new CheckPosition("RaceTable01", "HorseList");
            // タグの中にある馬名を探すオブジェクト
            CheckPosition checkHorseName = new CheckPosition("HorseInfo", "title=\"");
            // タグに挟まれたオッズを探すオブジェクト
            CheckPosition checkOdds = new CheckPosition("Txt_R Popular", "odds-1");
            // テーブルを閉じるタグを探すオブジェクト
            CheckPosition checkClose = new CheckPosition("tbody", "table");

            // 複数ページを検索する時はWebサーバに負荷をかけないように時間を置く
            // Thread.sleep(10000);
            URL searchUrl = new URL(url);
            InputStream is = searchUrl.openStream();
            InputStreamReader isr = new InputStreamReader(is, "euc-jp");

            int i = isr.read(); 
            while(i != -1) {
                // 出馬表にたどり着いたか
                if(checkHorseList.searchPosition((char)i)) {
                    Horse horse = new Horse();
                    // 馬名の抽出が完了しているか
                    // 完了している場合オッズの抽出を行う
                    String name = "";
                    while(name.isEmpty()) {
                        name = getInTagValue(checkHorseName, isr);
                    }
                    horse.setName(name);
                    String odds = "";
                    while(odds.isEmpty()) {
                        odds = getOutTagValue(checkOdds, isr);
                    }              
                    horse.setOdds(odds);
                    this.horses.add(horse);
                }
                // 出馬表の内容を抽出し終えたか
                if(!this.getHorses().isEmpty()) {
                    if(checkClose.searchPosition((char)i)) {
                        break;
                    }
                }
                // Webページを読み取りきっていない場合次の文字を読み取る
                if(i != -1) {
                    i = isr.read();
                }
            }
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * horsesの内容を出力する
     */
    public void printHorses() {
        System.out.printf("%-10s%-5s\n", "名前", "XX.X");
        for(int i = 0; i < this.horses.size(); i++) {
            System.out.println(this.horses.get(i));
        }
    }

    /**
     * 重賞レースの特集ページからraceIdを抽出する
     * @throws IOException
     */
    public void searchRaceId() throws IOException {
        try {
            // 特集ページのURL
            String url = "https://race.netkeiba.com/special/index.html?id=" + this.raceNumber;
            // レースIDを探すオブジェクト
            CheckPosition checkRaceId = new CheckPosition("navi_shutuba", "race_id=");

            URL searchUrl = new URL(url);
            InputStream is = searchUrl.openStream();
            InputStreamReader isr = new InputStreamReader(is, "euc-jp");

            while(this.raceId.isEmpty()) {
                setRaceId(getInTagValue(checkRaceId, isr));
            }
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * checkPosが探しているデータをタグの中から抽出する
     * まだデータのある場所に到達していない場合は空を返す
     * @param checkPos 特定のデータを探索するオブジェクト
     * @param isr inputStreamReader
     * @return value 目標データあるいは空を返す
     */
    public String getInTagValue(CheckPosition checkPos, InputStreamReader isr) {
        String value = "";
        try {
            int i = isr.read();
            if(checkPos.searchPosition((char)i)) {
                i = isr.read();
                while((char)i != '&' && (char)i != '\"') {
                    value += (char)i;
                    i = isr.read();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * checkPosが探しているデータをタグの間から抽出する
     * まだデータのある場所に到達していない場合は空を返す
     * @param checkPos 特定のデータを探索するオブジェクト
     * @param isr inputStreamReader
     * @return value 目標データあるいは空を返す
     */
    public String getOutTagValue(CheckPosition checkPos, InputStreamReader isr) {
        String value = "";
        try {
            int i = isr.read();
            if(checkPos.searchPosition((char)i)) {
                while((char)i != '>') {
                    i = isr.read();
                }
                i = isr.read();
                while((char)i != '<') {
                    value += (char)i;
                    i = isr.read();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
