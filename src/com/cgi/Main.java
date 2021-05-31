//+++2021-05-26 seisuga kood, mis sordib sisenemise kella aja alusel kogu listi
//+++probleem: peaks itereerima iga minuti tagant ja otsima vastavusi listis!
//+++probleem: if lauseid tuleb liiga palju et mõista iga sisse/välja aegade suhteid omavahel
package com.cgi;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // Read external file
        File file = new File("kylastusajad.txt");
        // Start scanner
        Scanner scan = null;
        scan = new Scanner(file);
        // Declaring DateTime formatter
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        // Creating empty Arraylists of arrival and exit
        List<LocalTime> arrival = new ArrayList<>();
        List<LocalTime> exit = new ArrayList<>();
        // Reading scanner line by line and splitting them at ',' and storing in list
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] lineArray = line.split(",");
            // Converting time strings to type LocalTime using predefined formatter and adding them to arrays
            arrival.add(LocalTime.parse(lineArray[0], timeFormatter));
            exit.add(LocalTime.parse(lineArray[1], timeFormatter));
        }
        // Closing the scanner
        scan.close();
        // Sorting 'arrival' and 'exit' arrays
        arrival.sort(Comparator.naturalOrder());
        exit.sort(Comparator.naturalOrder());

        // Declaring variables for finding max visitors
        int visitorsIn = 1; // The number of visitors currently in museum
        int maxVisitors = 1; // Max number of visitors in museum
        LocalTime timeStart = arrival.get(0); // Timestamp when max number of visitors event started
        LocalTime timeEnd = exit.get(0); // Timestamp when max number of visitors event ended
        int i = 1; int j = 0;
        // Processing all events in sorted order
        while (i < arrival.size() && j < arrival.size()) {
            // If next is 'arrival' -> visitorsIn +1
            if (arrival.get(i).isBefore(exit.get(j)) || arrival.get(i).equals(exit.get(j))) {
                visitorsIn++; // Adding visitor
                // Update maxVisitors if required
                if (visitorsIn > maxVisitors) {
                    maxVisitors = visitorsIn;
                    timeStart = arrival.get(i); // Storing maxVisitors starting time
                    timeEnd = exit.get(j); // Storing maxVisitors end time
                }
                i++; // arrival array index increment
            }
            else
            {
                visitorsIn--; // Subtracting visitor
                j++; // exit array index increment
            }
        }
        System.out.println("Max visitors " + maxVisitors + " between " + timeStart + " - " + timeEnd);
        System.out.println(Arrays.toString(new List[]{arrival}));
        System.out.println(Arrays.toString(new List[]{exit}));
    }
}