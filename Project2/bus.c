// Fill in empty functions and definitions

#include "pintos_headers.h"

struct station {
    int passengers;
    struct lock lock;
    struct condition board, arrived;
};

void station_setup(struct station *station) {
    station->passengers = 0;
    lock_init(&station->lock);
    cond_init(&station->board);
    cond_init(&station->arrived);
}

void bus_load_passengers(struct station *station, int count) {
    lock_acquire(&station->lock);
    while(station->passengers > 0 && count > 0){
        cond_signal(&station->arrived,&station->lock);
        cond_wait(&station->board, &station->lock);
        count--;
    }
    lock_release(&station->lock);
}

void passenger_waitfor_bus(struct station *station) {
    lock_acquire(&station->lock);
    station->passengers++;
    cond_wait(&station->arrived, &station->lock);
    lock_release(&station->lock);
}

void passenger_on_board(struct station *station) {
    lock_acquire(&station->lock);
    station->passengers--;
    cond_signal(&station->board, &station->lock);
    lock_release(&station->lock);
}