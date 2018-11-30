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
public class MinSpanTree {

    public boolean openFile(String text)
    {
        try
        {
            FileReader read = new FileReader(text);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
//    public boolean createTree(DisjSets dis, int possibleEdge)
//    {
//        int rt1 = dis.find(possibleEdge);
//    }
    
    public static void main(String[] args) throws IOException
    {
        String name;
        MinSpanTree mst = new MinSpanTree();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please input the name of the file you wish to read from: ");
        name = br.readLine();
        while(mst.openFile(name) == false)
        {
            System.out.println("Sorry. That file does not exist.");
            System.out.print("Please input the name of the file you wish to read from: ");
            name = br.readLine();
        }
        File file = new File(name);
        BufferedReader read = new BufferedReader(new FileReader(file));
        String input;
        String city1 = null;
        String city2;
        int count = 0;
        DistanceAndCities dAC[] = null;
        
        while((input = read.readLine()) != null)
        {
            boolean firstTwoCities = true;
            while(!"".equals(input))
            {
                count++;
                if(firstTwoCities == true && count == 1)
                {
                    city1 = input.substring(0, input.indexOf(","));
                    input = input.substring(input.indexOf(",") + 1, input.length());
                    city2 = input.substring(0, input.indexOf(","));
                    input = input.substring(input.indexOf(",") + 1, input.length());
                    int dist = Integer.parseInt(input.substring(0, input.indexOf(",")));
                    input = input.substring(input.indexOf(",") + 1, input.length());
                    DistanceAndCities temp[] = new DistanceAndCities[1];
                    temp[0] = new DistanceAndCities();
                    temp[0].distance = dist;
                    temp[0].start = city1;
                    temp[0].stop = city2;
                    city1 = city2;
                    dAC = temp;
                    firstTwoCities = false;
                }
                else if(firstTwoCities == true)
                {
                    city1 = input.substring(0, input.indexOf(","));
                    input = input.substring(input.indexOf(",") + 1, input.length());
                    city2 = input.substring(0, input.indexOf(","));
                    input = input.substring(input.indexOf(",") + 1, input.length());
                    int dist = Integer.parseInt(input.substring(0, input.indexOf(",")));
                    input = input.substring(input.indexOf(",") + 1, input.length());
                    DistanceAndCities temp[] = new DistanceAndCities[count];
                    System.arraycopy(dAC, 0, temp, 0, dAC.length);
                    temp[count - 1] = new DistanceAndCities();
                    temp[count - 1].start = city1;
                    temp[count - 1].stop = city2;
                    temp[count - 1].distance = dist;
                    city1 = city2;
                    dAC = temp;
                    firstTwoCities = false;
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
                    DistanceAndCities temp[] = new DistanceAndCities[count];
                    System.arraycopy(dAC, 0, temp, 0, dAC.length);
                    temp[count - 1] = new DistanceAndCities();
                    temp[count - 1].start = city1;
                    temp[count - 1].stop = city2;
                    temp[count - 1].distance = dist;
                    city1 = city2;
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
        DistanceAndCities temp = new DistanceAndCities(); //used to compare weight from pq and distance between cities
        DistanceAndCities newArray[] = new DistanceAndCities[count];    //this will be the sorted array
        for(int i = 0; i < count; i++)                                  //initialize each instance of the array
        {
            newArray[i] = new DistanceAndCities();
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
            DistanceAndCities tempArray[] = new DistanceAndCities[counter]; //used to reduce size of dAC
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
        
        for(int i = 0; i < dAC.length; i++) //for each element in dAC
        {
            System.out.println(i + ": " + dAC[i].start + "<--" + dAC[i].distance + "-->" + dAC[i].stop);    //print the info
        }
        
        DisjSets ds = new DisjSets(count);
        int numEdges = 0;
        
    }
}