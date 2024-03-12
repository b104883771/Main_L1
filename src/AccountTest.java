import java.util.HashMap;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @org.junit.jupiter.api.Test
    void testUnDo() throws NothingToUndo {
        Account a = new Account("12345678901234567890", "Onegin");
        String aold = a.getAcc_name();
        a.setAcc_name("09876543210987654321");
        a.unDo();
        Assert.assertEquals(aold, a.getAcc_name());
    }

    @org.junit.jupiter.api.Test
    void load() {
        HashMap<CurName, Integer> curOld;
        Account a = new Account("12345678901234567890", "Onegin");
        a.setCurrency(CurName.EUR, 100);
        String accOld = a.getAcc_name();
        String cliOld = a.getClient();
        curOld = a.getCurrency();
        Loadable l = a.Save();
        a.setClient("Petrov");
        a.setAcc_name("09876543210987654321");
        a.setCurrency(CurName.USD, 300);
        l.load();
        Assert.assertEquals(accOld, a.getAcc_name());
        Assert.assertEquals(cliOld, a.getClient());
        Assert.assertEquals(curOld, a.getCurrency());
    }

}