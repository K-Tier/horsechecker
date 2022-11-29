package main;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // 重賞レースの場合特集ページあり
        System.out.print("レース特集ページはありますか(y / n) >> ");
        Scanner scanner = new Scanner(System.in);
        String gradeRace = scanner.nextLine();
        Race testRace;
        if(gradeRace.equals("y")) {
            // 特集ページのURLに記述されている4桁のidを入力
            System.out.print("URLに表示されているidを入力してください >> ");
            String raceNumber = scanner.nextLine();
            testRace = new Race(raceNumber);
            System.out.println("目的のレースID：" + testRace.getRaceId());
        } else {
            testRace = new Race(null);
            testRace.setRaceId("202205050307");
        }
        scanner.close();
        testRace.setHorses();
        testRace.printHorses();
        }
}