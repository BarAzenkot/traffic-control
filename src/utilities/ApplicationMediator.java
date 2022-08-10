package utilities;

import java.util.ArrayList;

public class ApplicationMediator implements Mediator {

    private ArrayList<Colleague> colleagues;

    public ApplicationMediator() { colleagues = new ArrayList<Colleague>(); }

    public void addColleague(Colleague colleague) { colleagues.add(colleague); }

    @Override
    public void send(Object obj, Colleague originator) {
        for(Colleague colleague1 : colleagues) {
            if(colleague1 != originator)
                colleague1.receive(obj);
        }
    }
}
