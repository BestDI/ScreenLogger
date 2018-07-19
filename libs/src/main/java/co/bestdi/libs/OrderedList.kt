package co.bestdi.libs

import java.util.*


/** https://stackoverflow.com/questions/18144820/inserting-into-sorted-linkedlist-java
 * It is a way to insert each item in the sorted position, so that we do not have to
 * sort the list after each insertion
 * here, we use listIterator the complexity for doing get will be O(1).
 * use this class with orderedAdd & orderedAddAll to make it work
 */

class OrderedList<T : Comparable<T>>(private val isAscendingOrder: Boolean = true) : LinkedList<T>() {
    private val serialVersionUID = 1L

    override fun add(element: T): Boolean {
        val listIterator = listIterator()
        while (true) {
            if (!listIterator.hasNext()) {
                listIterator.add(element)
                return true
            }
            val nextElement = listIterator.next()
            val isCorrectPosition = if (isAscendingOrder) element < nextElement else element > nextElement
            if (isCorrectPosition) {
                listIterator.previous()
                listIterator.add(element)
                return true
            }
        }
    }

    override fun addAll(elements: Collection<T>): Boolean {
        var isAllAdded = true
        elements.forEach {
            isAllAdded = add(it) and isAllAdded
        }
        return isAllAdded
    }
}