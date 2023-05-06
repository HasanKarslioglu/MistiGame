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
	public static ArrayList<Player> playerList;
	public Top10List(Main playerList) {
      Top10List.playerList = Main.getPlayerList();
  }
	// SETTER FOR playerList
	public static void setPlayerList(ArrayList<Player> newList) {
		Top10List.playerList = newList;
	} 
	public static void top10Func() {
		String fileName = "Top10List.txt";
        File file = new File(fileName);
        ArrayList<String> topPlayers = new ArrayList<>();
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
                ArrayList<String> oldPlayers = new ArrayList<>();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                   topPlayers.add(line.trim());
                   oldPlayers.add(line.trim());
                }
                try {
                	String tempPlayer = highestScorePlayer.getName() + " "  + Integer.toString(highestScorePlayer.getScore());
                	topPlayers.add(tempPlayer);

				} catch (Exception e) {
				}
                ArrayList<String> playerNames = new ArrayList<>();
                ArrayList<Integer> playerScores = new ArrayList<>();
                for (String player : topPlayers) {
                    String[] tokens = player.split(" ");
                    playerNames.add(tokens[0]);
                    //playerScores.add(Integer.parseInt(tokens[1]));
                    try {
                        int score = Integer.parseInt(tokens[1]);
                        playerScores.add(score);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid score: " + tokens[1]);
                    }
                }
                Collections.sort(playerScores,Comparator.reverseOrder());
             // playerNames arraylistini sıralama öncesi bir kopyasını oluştur
                ArrayList<String> sortedPlayerNames = new ArrayList<>(playerNames);

                // playerNames arraylistini, playerScores'a göre sırala
                for (int i = 0; i < playerScores.size(); i++) {
                    int index = sortedPlayerNames.indexOf(playerNames.get(i)); // ismin bulunduğu indeksi al
                    playerNames.set(i, sortedPlayerNames.get(index)); // yeni sıraya göre ismi güncelle
                }
                Scanner okuyucu = new Scanner(file);
                boolean satirinSonu = false;
                FileWriter writer = new FileWriter(file,true);
                BufferedWriter bufWriter = new BufferedWriter(writer);
                while (okuyucu.hasNextLine()) {
                    String satir = okuyucu.nextLine();
                    if (satir.endsWith("\n") || satir.endsWith("\r\n")) {
                        satirinSonu = true;
                    } else {
                        satirinSonu = false;
                    }
                }
                // Bir önceki satırın sonuna gelinmemişse yeni bir satır başlatıyoruz
                if (!satirinSonu) {
                    bufWriter.newLine();
                }
                for (int i = 0; i < playerScores.size(); i++) {
                    int score = playerScores.get(i);
                    String playerName = playerNames.get(i);
                    try {
                        if (!oldPlayers.contains(playerName)) {
                        	bufWriter.write(playerName + " " + score + "\n");
                        } else {
                            System.out.println(playerName + " adlı oyuncu daha önce kaydedilmiş, bu oyuncu atlanıyor.");
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred while writing to file: " + e.getMessage());
                    }
                }

                
                bufferedReader.close();
                fileReader.close();
                bufWriter.close();
                

            } catch (Exception e) {
                e.printStackTrace();
            }

        } 
        //If the Top10List.txt file does not exist, this else line will create the file 
        else {
        	try {
                    file.createNewFile();
                    top10Func();

            } catch (IOException e) {
                System.out.println("An error occurred while creaiting to file: " + e.getMessage());
            }
        }

    }
}
//Helper class for the ScoreComparator
 class ScoreComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2) {
        return Integer.compare(p1.getScore(), p2.getScore());
    }
}
 