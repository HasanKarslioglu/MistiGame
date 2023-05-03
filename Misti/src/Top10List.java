import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Top10List {
	public static void top10Func() {
		String fileName = "Top10List.txt";
        File file = new File(fileName);
        ArrayList<String> topPlayers = new ArrayList<>();
        ArrayList<Player> playerList = new ArrayList<>();
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
                while ((line = bufferedReader.readLine()) != null) {
                   topPlayers.add(line.trim());
                }
                try {
                	String tempPlayer = highestScorePlayer.getName() + " "  + Integer.toString(highestScorePlayer.getScore());
                	topPlayers.add(tempPlayer);
					
				} catch (Exception e) {
				}
                //String tempPlayer = highestScorePlayer.getName() + " "  + Integer.toString(highestScorePlayer.getScore());
                //topPlayers.add(tempPlayer);
                ArrayList<String> playerNames = new ArrayList<String>();
                ArrayList<Integer> playerScores = new ArrayList<Integer>();
                for (String player : topPlayers) {
                    String[] tokens = player.split(" ");
                    playerNames.add(tokens[0]);
                    playerScores.add(Integer.parseInt(tokens[1]));
                }
                Collections.sort(playerScores, Comparator.reverseOrder());
                for (Integer score : playerScores) {
                    int index = playerScores.indexOf(score);
                    try (FileWriter writer = new FileWriter(file,true)) {
                    	writer.write(playerNames.get(index) + "  " + score);
                    } catch (Exception e) {
                        System.out.println("An error occurred while writing to file: " + e.getMessage());
                    }
                }
                bufferedReader.close();
                fileReader.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else {
        	try {
                    file.createNewFile();
                    top10Func();

            } catch (IOException e) {
                System.out.println("An error occurred while creaiting to file: " + e.getMessage());
            }
        }
            
    }
}
class ScoreComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2) {
        return Integer.compare(p1.getScore(), p2.getScore());
    }
}