package org.netmelody.citric.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

import com.google.common.collect.Iterators;

public final class HeadedSortedSet<E> implements SortedSet<E> {
    final SortedSet<E> delegate;
    final E head;

    public HeadedSortedSet(SortedSet<E> delegate, E head) {
        this.delegate = checkNotNull(delegate);
        // check that head is greater than delegate.first() if delefate is not empty
        this.head = checkNotNull(head);
    }

    @Override
    public int size() {
        return this.delegate.size() + 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (this.head.equals(o)) {
            return true;
        }
        return this.delegate.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return Iterators.concat(Iterators.singletonIterator(this.head), this.delegate.iterator());
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.delegate.comparator();
    }

    @Override
    public E first() {
        return this.head;
    }

    @Override
    public E last() {
        return this.delegate.isEmpty() ? this.head : this.delegate.last();
    }

    @Override public SortedSet<E> headSet(E toElement) { throw new UnsupportedOperationException(); }
    @Override public SortedSet<E> tailSet(E fromElement) { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean add(E e) { throw new UnsupportedOperationException(); }
    @Override public boolean remove(Object o) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public void clear() { throw new UnsupportedOperationException(); }
    @Override public SortedSet<E> subSet(E fromElement, E toElement) { throw new UnsupportedOperationException(); }
}