package prac.ch6.referece.weakReferenceDemo1;

import java.lang.ref.WeakReference;

/**
 * Salad class
 * 继承WeakReference，将Apple作为弱引用。
 * 注意到时候回收的是Apple，而不是Salad
 *
 */
public class Salad extends WeakReference<Apple> {
    public Salad(Apple apple) {
        super(apple);
    }
}
