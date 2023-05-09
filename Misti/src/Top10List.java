import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Top10List {
	
	//-----------VARIABLES------------
	private static ArrayList<Player> playerList;
    private static ArrayList<String> topPlayers = new ArrayList<>();
    private static ArrayList<String> playerNames = new ArrayList<>();
    private static ArrayList<Integer> playerScores = new ArrayList<>();
	
  //-----------METHODS------------
	public static void top10Func() {
		String fileName = "Top10List.txt";
        File file = new File(fileName);
        Collections.sort(playerList, new ScoreComparator());
        int highestScore = Integer.MIN_VALUE;
        Player highestScorePlayer = null;
        for (Player player : playerList) {
            if (player.getScore() > highestScore) {
                highestScore = player.getScore();
                highestScorePlayer = player;
            }
        }
        if(file.exists()) {
        	try {
                FileReader fileReader = new FileReader("Top10List.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {topPlayers.add(line.trim());}
                try {
                	String tempPlayer = highestScorePlayer.getName() + " "  + Integer.toString(highestScorePlayer.getScore());
                	topPlayers.add(tempPlayer);
				} catch (Exception e) {
					
				} for (String player : topPlayers) {
                    String[] tokens = player.split(" ");
                    playerNames.add(tokens[0]);
                    try {
                        int score = Integer.parseInt(tokens[1]);
                        playerScores.add(score);
                    } catch (Exception e) {
                    	
                    	}
                } // playerScores ve playerNames listelerini sıralama öncesi bir kopyalarını oluştur
				ArrayList<Integer> sortedPlayerScores = new ArrayList<>(playerScores);
				ArrayList<String> sortedPlayerNames = new ArrayList<>(playerNames);
				// playerScores listesini, öğeleri ters sırayla sırala
				Collections.sort(sortedPlayerScores, Comparator.reverseOrder());

				// playerNames ve playerScores listelerini yeni sıraya göre güncelle
				for (int i = 0; i < sortedPlayerScores.size(); i++) {
				    try {
				    	int index = playerScores.indexOf(sortedPlayerScores.get(i)); // skorun bulunduğu indeksi al
					    playerScores.set(i, sortedPlayerScores.get(i)); // yeni sıraya göre skoru güncelle
					    playerNames.set(i, sortedPlayerNames.get(index)); // yeni sıraya göre ismi güncelle
					} catch (Exception e) {}
				}
                Scanner okuyucu = new Scanner(file);
                boolean satirinSonu = false;
                FileWriter writer = new FileWriter(file,true);
                BufferedWriter bufWriter = new BufferedWriter(writer);
                while (okuyucu.hasNextLine()) {
                    String satir = okuyucu.nextLine();
                    if (satir.endsWith("\n") || satir.endsWith("\r\n")) {} else {satirinSonu = false;}
                }
                // Bir önceki satırın sonuna gelinmemişse yeni bir satır başlatıyoruz
                if (!satirinSonu) {bufWriter.newLine();}
                bufWriter.close();
                FileWriter newWriter = new FileWriter(file);
                for (int i = 0; i < Math.min(10, playerScores.size()); i++) {
                    int score = playerScores.get(i);
                    String playerName = playerNames.get(i);
                    try {
                    	if(okuyucu.hasNextLine()) {newWriter.write(playerName + " " + score + "\n");}
                        else {newWriter.write(playerName + " " + score + "\n");}
                    } catch (Exception e) {System.out.println("An error occurred while writing to file: " + e.getMessage());}
                }
                bufferedReader.close();
                fileReader.close();
                newWriter.close();
                okuyucu.close();
            } catch (Exception e) {e.printStackTrace();}
        }
        //If the Top10List.txt file does not exist, this else' line will create the file 
        else {
        	try {
                    file.createNewFile();
                    top10Func();
            } catch (Exception e) {System.out.println("An error occurred while creaiting to file: " + e.getMessage());}
        }
    }
	//-----------SETTERS------------
		public static void setPlayerList(ArrayList<Player> newList) {Top10List.playerList = newList;} 
}
    //Helper class for the ScoreComparator
 class ScoreComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2) {
        return Integer.compare(p1.getScore(), p2.getScore());
    }
}
 