package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Race {
    /**
     * トップページのID
     */
    private String raceNumber;
    
    /**
     * 出馬表などに繋がるレースID
     */
    private String raceId;
    
    /**
     * レースに出場する競走馬のリスト
     */
    private ArrayList<Horse> horses;
    
    /**
     * コンストラクタ
     * @param raceNumber レースのトップページID
     */
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
    
    public void setHorses() throws IOException {
        try {
            String url = "https://race.netkeiba.com/race/shutuba.html?race_id=" + this.raceId;
            URLConnection searchUrl = new URL(url).openConnection();
            InputStream is = searchUrl.getInputStream();            Thread.sleep(10000);
            InputStreamReader isr = new InputStreamReader(is, "euc-jp");
            CheckId checkHorseList = new CheckId("RaceTable01", "HorseList");
            CheckId checkHorseName = new CheckId("HorseInfo", "title=\"");
            CheckId checkOdds = new CheckId("Txt_R Popular", "odds-1");
            CheckId checkClose = new CheckId("tbody", "table");
            int i = isr.read(); 
            while(i != -1) {
                Horse horse = new Horse();
                if(checkHorseList.searchIdName((char)i)) {
                    i = isr.read();
                    while(i != -1) {
                        if(horse.getName().isEmpty()) {
                            if(checkHorseName.searchIdName((char)i)) {
                                i = isr.read();
                                String name = "";
                                while((char)i != '&' && (char)i != '\"') {
                                    name += (char)i;
                                    i = isr.read();
                                }
                                horse.setName(name);
                                
                                // System.out.println("nameSetComplete:\t" + horse.getName());
                            }
                        } else {
                            if(checkOdds.searchIdName((char)i)) {
                                i = isr.read();
                                String odds = "";
                                while((char)i != '>') {
                                    i = isr.read();
                                }
                                i = isr.read();
                                while((char)i != '<') {
                                    odds += (char)i;
                                    i = isr.read();
                                }                                
                                horse.setOdds(odds);
                                this.horses.add(horse);
                                
                                // System.out.println("oddsSetComplete:\t" + horse.getOdds());
                                checkHorseList.setIdName("");
                                checkHorseName.setIdName("");
                                checkOdds.setIdName("");
                                break;
                            }
                        }
                        i = isr.read();
                    }
                }
                if(!this.getHorses().isEmpty()) {
                    if(checkClose.searchIdName((char)i)) {
                        break;
                    }
                }
                if(i != -1) {
                    i = isr.read();
                }
            }
            isr.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void printHorses() {
        System.out.printf("%-10s%-5s\n", "名前", "XX.X");
        for(int i = 0; i < this.horses.size(); i++) {
            System.out.println(this.horses.get(i));
        }
    }

    public void searchRaceId() throws IOException {
        try {
            String url = "https://race.netkeiba.com/special/index.html?id=" + this.raceNumber;
            URL searchUrl = new URL(url);
            InputStream is = searchUrl.openStream();
            InputStreamReader isr = new InputStreamReader(is, "euc-jp");
            int i = isr.read();
            CheckId checkRaceId = new CheckId("navi_shutuba", "race_id=");
            while(i != -1) {
                if(checkRaceId.searchIdName((char)i)) {
                    i = isr.read();
                    while((char)i != '&' && (char)i != '\"') {
                        setRaceId(this.raceId + (char)i);
                        i = isr.read();
                    }
                    break;
                }
                i = isr.read();
            }
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
