package be.unamur.fpgen.utils;

/**
 * This class was used tduring the development stage, before using openAi to generate conversation
 * We simply created a conversation with a list of fictif messages
 * Not used for real conditions
 */
public class Alternator<T> {
        private T first;
        private T second;
        private int returnFirstNext;

        public Alternator(T first, T second) {
            this.first = first;
            this.second = second;
            this.returnFirstNext = 0;
        }

        public T getNext() {
            if (returnFirstNext == 0) {
                returnFirstNext++;
                return first;
            } else if(returnFirstNext == 1){
                returnFirstNext++;
                return second;
            } else {
                returnFirstNext = 0;
                return second;
            }
        }
}
