package com.example.googlemap;

public interface AirMapViewBuilder<T extends AirMapInterface, Q> {

  AirMapViewBuilder<T, Q> withOptions(Q arg);

  T build();
}
