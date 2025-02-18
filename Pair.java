
/**
 * P.
 *
 * @author Alexander Sukhin
 * @version 19/02/2025
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
