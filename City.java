/*
 * Mark Hewitt
 * mdh160430
 */
package minspantree;

/**
 *
 * @author hewit
 */
public class City {
    String name;
    int numPointers = 0;
    City pointer[] = new City[numPointers];
    int distance[] = new int[numPointers];
    boolean root = false;
    boolean parent = false;
    boolean child = false;
    
    public void addPointer(City cit, int dis)
    {
        numPointers++;
        City tempPointer[] = pointer;
        pointer = new City[numPointers];
        pointer = tempPointer;
        pointer[numPointers - 1] = cit;
        int tempDistance[] = distance;
        distance = new int[numPointers];
        distance = tempDistance;
        distance[numPointers - 1] = dis;
    }
    
    public void setRoot(boolean boo)
    {
        root = boo;
    }
    
    public void setChild(boolean boo)
    {
        child = boo;
    }
    
    public void setParent(boolean boo)
    {
        parent = boo;
    }
}
