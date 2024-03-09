public class UnboundedIntTester {
    public static void main(String[] args){
        UnboundedInt unboundedInt = new UnboundedInt("113414142314124");
        UnboundedInt unboundedInt2 = new UnboundedInt("2");
        UnboundedInt unboundedInt3 = unboundedInt.add(unboundedInt2);
        System.out.println(unboundedInt3);
    }
}
