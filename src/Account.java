import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

class NothingToUndo extends Exception {
}

enum CurName {USD, RUR, EUR};

interface Command {
    public void perform();
}

interface Loadable {
    void load();
}

public class Account {
    private Deque<Command> commands = new ArrayDeque<>(); // очередь - поле Account
    private String acc_name;

    public String getAcc_name() {
        return acc_name;
    }

    private Account() {
    }

    ;

    public Account unDo() throws NothingToUndo {
        if (commands.isEmpty()) throw new NothingToUndo();
        commands.pop().perform();
        return this;
    }

    public Loadable Save() {
        return new Snapshot();
    }

    private class Snapshot implements Loadable {
        private String acc_name;
        private String client;
        private HashMap<CurName, Integer> currency;

        public Snapshot() {
            this.acc_name = Account.this.acc_name;
            this.client = Account.this.client;
            this.currency = new HashMap<>(Account.this.currency);
        }

        @Override
        public void load() {
            Account.this.acc_name = this.acc_name;
            Account.this.client = this.client;
            Account.this.currency = new HashMap<>(this.currency);
        }
    }

    public void setAcc_name(String acc_name) {
        if (acc_name.length() != 20) throw new IllegalArgumentException("account !=20");
        String acc_nameOld = this.acc_name;
        this.commands.push(() -> {
            this.acc_name = acc_nameOld;
        }); // действие обратное
        this.acc_name = acc_name;
    }

    private String client;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        if (client == null || client.isEmpty()) throw new IllegalArgumentException("client is null");
        String clientOld = this.client;
        this.commands.push(() -> {
            this.client = clientOld;
        }); // действие обратное
        this.client = client;
    }

    public Account(String acc_name, String client) {
        this.setClient(client);
        this.setAcc_name(acc_name);
        this.currency = new HashMap<>();
    }

    private HashMap<CurName, Integer> currency;

    public HashMap<CurName, Integer> getCurrency() {
        return new HashMap<CurName, Integer>(this.currency);
    }

    public void setCurrency(CurName name, Integer value) {
        if (value < 0) throw new IllegalArgumentException("Value currency < 0");
        if (currency.containsKey(name)) {
            //Integer i = this.currency.getOrDefault(name,value);
            Integer i = this.currency.get(name);
            this.commands.push(() -> {
                this.currency.put(name, i);
            }); // должно быть предыдущее значение
        } else {
            this.commands.push(() -> {
                this.currency.remove(name);
            });
        }
        this.currency.put(name, value);
    }

    public void printcur() {
        this.currency.values().stream().forEach(System.out::println);
    }
}
