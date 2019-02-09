package designchallenge1;

public abstract class ViewObserver {
    protected EventSubject subject;
    public abstract void update();
}
