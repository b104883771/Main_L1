public class Main_L1 {
    public static void main(String[] args) throws NothingToUndo {
        Account acc = new Account("40701810000000000000", "Petrov");
        acc.setClient("Ivanov");
        acc.setAcc_name("40701810000000000000");
        //acc.setCurrency(CurName.EUR,50);
        //acc.setCurrency(CurName.USD,25);
        //acc.setCurrency(CurName.RUR,5000);
        //acc.setCurrency(CurName.RUR,2000);
        acc.setCurrency(CurName.RUR, 3000);
        acc.setCurrency(CurName.EUR, 700);
        acc.setCurrency(CurName.USD, 100);
        Loadable snap = acc.Save();
        //System.out.println(acc.getClient());
        acc.printcur();
        acc.setCurrency(CurName.EUR, 500);
        acc.printcur();
        snap.load();
        acc.printcur();


    }
}
