
/**
 * Write a description of class Pair here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
class Pair<F, S>
{
    private final F first;
    private final S second;
    
    public Pair(F first, S second)
    {
        this.first = first;
        this.second = second;
    }
    
    public F first()
    {
        return first;
    }
    
    public S second()
    {
        return second;
    }
}
