/*
 * Mark Hewitt
 * mdh160430
 */
package minspantree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

/**
 *
 * @author hewitt
 */
public class Kruskals {
    int distance = 0;       //mileage between cities
    String start = "";      //starting city
    String stop = "";       //destination city

    public boolean openFile(String text)
    {
        try     //try to open file
        {
            FileReader read = new FileReader(text);
            return true;
        }
        catch(Exception e)
        {
            return false;       //file could not be opened
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        String name;        //hold input from user
        Kruskals mst = new Kruskals();    //used to call openFile
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   //used to read input from user
        System.out.print("Please input the name of the file you wish to read from: ");  //prompt user for filename
        name = br.readLine();       //read input
        while(mst.openFile(name) == false)      //while the file cannot be opened
        {
            System.out.println("Sorry. That file does not exist.");     //tell user that the file does not exist
            System.out.print("Please input the name of the file you wish to read from: ");  //ask again for the filename
            name = br.readLine();           //get input from user
        }
        File file = new File(name);     //file
        BufferedReader read = new BufferedReader(new FileReader(file));     //read from file
        String input;       //input from file
        String city1 = null;        //first city in input
        String city2;       //other cities in input
        String cities[] = null;     //list of the cities
        int count = 0;      //count all the cities
        Kruskals dAC[] = null;     //array of edges
        String cityNames = "";
        int num = 0;
        
        while((input = read.readLine()) != null)        //while file can still read in from file
        {
            boolean firstTwoCities = true;      //first two cities
            while(!"".equals(input))        //while input is not empty
            {
                count++;            //increment count
                if(firstTwoCities == true && count == 1)    //if the array is empty and it is the first two cities. 
                {
                    city1 = input.substring(0, input.indexOf(","));     //first city name
                    input = input.substring(input.indexOf(",") + 1, input.length());        //remove first city and comma from string input
                    city2 = input.substring(0, input.indexOf(","));         //city 2 from input
                    input = input.substring(input.indexOf(",") + 1, input.length());        //remove city 2 from input and comma
                    int dist = Integer.parseInt(input.substring(0, input.indexOf(",")));        //get distance between first city and second city
                    input = input.substring(input.indexOf(",") + 1, input.length());            //remove distance from input string
                    Kruskals temp[] = new Kruskals[1];        //make temp array that is 1 larger than current dAC array
                    temp[0] = new Kruskals();      //initialize the new element
                    temp[0].distance = dist;        //pass in distance
                    temp[0].start = city1;      //pass starting city 
                    temp[0].stop = city2;       //pass in destination
                    dAC = temp;     //assign dAC to array that holds newly hashed info
                    String tempCities[] = new String[1];    //temp array to hold name of all cities
                    tempCities[0] = "";     //initialize
                    tempCities[0] = city1;      //add
                    cities = tempCities;        //assign temp array to perm array
                    firstTwoCities = false;     //no longer first two cities.
                    cityNames += ("," + String.valueOf(num) + "." + city1);
                    num++;
                }
                else if(firstTwoCities == true)
                {
                    city1 = input.substring(0, input.indexOf(","));
                    input = input.substring(input.indexOf(",") + 1, input.length());
                    city2 = input.substring(0, input.indexOf(","));
                    input = input.substring(input.indexOf(",") + 1, input.length());
                    int dist = Integer.parseInt(input.substring(0, input.indexOf(",")));
                    input = input.substring(input.indexOf(",") + 1, input.length());
                    Kruskals temp[] = new Kruskals[count];
                    System.arraycopy(dAC, 0, temp, 0, dAC.length);
                    temp[count - 1] = new Kruskals();
                    temp[count - 1].start = city1;
                    temp[count - 1].stop = city2;
                    temp[count - 1].distance = dist;
                    dAC = temp;
                    String tempCities[] = new String[cities.length + 1];
                    System.arraycopy(cities, 0, tempCities, 0, cities.length);
                    tempCities[cities.length] = city1;
                    cities = tempCities;
                    firstTwoCities = false;
                    cityNames += ("," + String.valueOf(num) + "." + city1);
                    num++;
                }
                else
                {
                    city2 = input.substring(0, input.indexOf(","));
                    input = input.substring(input.indexOf(",") + 1, input.length());
                    int dist;
                    try
                    {
                        dist = Integer.parseInt(input.substring(0, input.indexOf(",")));
                        input = input.substring(input.indexOf(",") + 1, input.length());
                    }
                    catch(Exception e)
                    {
                        dist = Integer.parseInt(input);
                        input = "";
                    }
                    Kruskals temp[] = new Kruskals[count];
                    System.arraycopy(dAC, 0, temp, 0, dAC.length);
                    temp[count - 1] = new Kruskals();
                    temp[count - 1].start = city1;
                    temp[count - 1].stop = city2;
                    temp[count - 1].distance = dist;
                    dAC = temp;             
                }
            }
        }
             
        PriorityQueue pq = new PriorityQueue(count);    //create priority queue
        for(int i = 0; i < count; i++)                  //add all the weights to the pq
        {
            pq.add(dAC[i].distance);
        }
        int poll;                                       //used to poll numbers out of pq
        int counter = count;                            //decrement each time dAC is resized to slowly shrink it
        int next = 0;                                   //keeps track of the next index that newArray will take
        Kruskals temp = new Kruskals(); //used to compare weight from pq and distance between cities
        Kruskals newArray[] = new Kruskals[count];    //this will be the sorted array
        for(int i = 0; i < count; i++)                                  //initialize each instance of the array
        {
            newArray[i] = new Kruskals();
        }
        
        while(dAC.length >= 1)   //while dAC has more than or equal to 1 element in the array
        {
            poll = (int)pq.poll();  //gets lowest number from pq and then removes the number from pq. number comes in form of object, so it is cast as an int.
            temp = dAC[0];      //get the first element of the array
            int index = 0;      //keep track of what element temp represents.
            while(temp.distance != poll)    //while the distance in temp does not match the lowest number from the pq.
            {
                index++;                    //increment the index 
                temp = dAC[index];          //get new element for comparison
            }
            newArray[next] = temp;          //add the lowest distance and corresponding cities to the new array
            next++;                         //increment so that next time, the next element in newArray will be replaced
            counter--;                      //dAC loses 1 element and will be reduced
            Kruskals tempArray[] = new Kruskals[counter]; //used to reduce size of dAC
            if(index == 0)                  //if the element with the lowest number was found at index 0
            {
                System.arraycopy(dAC, 1, tempArray, 0, dAC.length - 1); //copy every element in dAC to tempArray except for the first element
            }
            else
            {
                System.arraycopy(dAC, 0, tempArray, 0, index);      //copy every element up to the index of element to be removed. index is not included
                System.arraycopy(dAC, index + 1, tempArray, index, counter - index);    //copy every element after the index of removed element. index is not included
            }
            dAC = tempArray;    //tempArray which has 1 less element than dAC is not copied to dAC
        }
        dAC = newArray; //the newly sorted array is then assigned to dAC
        
        for(int i = 0; i < dAC.length; i++)
        {
            System.out.println((i+1) + ": " + dAC[i].start + "<--" + dAC[i].distance + "-->" + dAC[i].stop);
        }
        
        DisjSets ds = new DisjSets(cities.length);      //create disjoint set
        
        Kruskals minSpanTree[] = new Kruskals[num];     //create final minimum spanning tree
        
        for(int i = 0; i < cities.length - 1; i++)      //initalize the new array of Kruskals
        {
            minSpanTree[i] = new Kruskals();
        }
        
        int e = 0;      //count the edges of the new tree
        int i = 0;
        int node1 = 0;      //first node found in cities[]
        int node2 = 0;      //second node found in cities[]
        while(e != cities.length - 1)
        {
            Kruskals nextDisAndCit = new Kruskals();      //used to hold the next edge
            nextDisAndCit = dAC[i];       //assign an edge to newly created object

            String tempString = cityNames;
            tempString = tempString.substring(0, tempString.indexOf(dAC[i].start) - 1);
            tempString = tempString.substring(tempString.lastIndexOf(",") + 1, tempString.length());
            node1 = Integer.parseInt(tempString);

            tempString = cityNames;
            tempString = tempString.substring(0, tempString.indexOf(dAC[i].start) - 1);
            tempString = tempString.substring(tempString.lastIndexOf(",") + 1, tempString.length());
            node2 = Integer.parseInt(tempString);

            int x = ds.find(node1);     //find the root
            int y = ds.find(node2);     //find the root

            if(x != y)      //if the roots dont match,
            {
                minSpanTree[e++] = nextDisAndCit;       //assign value to minSpanTree
                ds.union(node1, node2);     //union the two nodes in disjsets
            }
            i++;
        }
        int totalDist = 0;      //track total distance
        for(int m = 0; m < minSpanTree.length - 1; m++)     //for each element in the tree
        {
            totalDist += minSpanTree[m].distance;       //add distance to total mileage
            System.out.println((m + 1) + ":" + minSpanTree[m].start + "<--" + minSpanTree[m].distance + "-->" + minSpanTree[m].stop);       //print info
        }
        System.out.println("The total distance for the tree is: " + totalDist);     //print total mileage
    }
}