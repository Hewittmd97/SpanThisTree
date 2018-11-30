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

/**
 *
 * @author hewit
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
        int numTrees = 0;
        City cityTrees[] = new City[numTrees];
        while((input = read.readLine()) != null)
        {
            numTrees++;
            System.out.println(numTrees);
            City tempTree[] = cityTrees;
            cityTrees = new City[numTrees];
            cityTrees = tempTree;
            City newCity = new City();
            newCity.root = true;
            newCity.name = input.substring(0, input.indexOf(",") - 1);
            input = input.substring(input.indexOf("," + 1), input.length());
            cityTrees[numTrees - 1] = new City();
            cityTrees[numTrees - 1] = newCity;
            while(!"".equals(input))
            {
                newCity = new City();
                newCity.name = input.substring(0, input.indexOf(",") - 1);
                input = input.substring(input.indexOf("," + 1), input.length());
                int miles;
                try
                {
                    miles = Integer.parseInt(input.substring(0, input.indexOf(",") - 1));
                    input = input.substring(input.indexOf("," + 1), input.length());
                }
                catch(Exception e)
                {
                    miles = Integer.parseInt(input);
                    input = "";
                }
                if(cityTrees[numTrees - 1].child == false)
                {
                    cityTrees[numTrees - 1].child = true;
                    cityTrees[numTrees - 1].addPointer(newCity, miles);
                    newCity.parent = true;
                    newCity.addPointer(cityTrees[numTrees - 1], miles);
                }
                else
                {
                    City parentCity = cityTrees[numTrees - 1].pointer[0];
                    while(parentCity.child == true)
                    {
                        parentCity = parentCity.pointer[1];
                    }
                    parentCity.child = true;
                    parentCity.addPointer(newCity, miles);
                    newCity.parent = true;
                    newCity.addPointer(parentCity, miles);
                }
            }
        }
    }
}
