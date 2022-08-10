package utilities;

public abstract class Colleague {

    private Mediator mediator;

    public Colleague (Mediator m) {
        mediator = m;
    }


    public void send(Object obj) {
        mediator.send(obj, this);
    }

    public Mediator getMediator() { return mediator; }

    public abstract void receive(Object obj);

}
