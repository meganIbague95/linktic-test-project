<template>
  <q-page class="flex flex-center column q-gutter-lg">
    <div class="text-h4 q-mb-lg">Microservices App</div>

    <q-btn
      label="Gestión de Productos"
      color="primary"
      size="lg"
      @click="$router.push('/products')"
    />

    <q-btn label="Ver Inventario" color="secondary" size="lg" @click="$router.push('/inventory')" />

    <q-btn label="Realizar Compra" color="positive" size="lg" @click="$router.push('/purchase')" />
  </q-page>
</template>
