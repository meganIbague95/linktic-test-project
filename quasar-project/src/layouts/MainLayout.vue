<template>
  <q-layout view="hHh lpR fFf">
    <!-- HEADER -->
    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn flat dense round icon="menu" @click="toggleLeftDrawer" />
        <q-toolbar-title>Inventory App</q-toolbar-title>
      </q-toolbar>
    </q-header>

    <!-- SIDEBAR -->
    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <q-list padding>
        <q-item clickable v-ripple @click="go('/products')">
          <q-item-section avatar><q-icon name="add_box" /></q-item-section>
          <q-item-section>Crear producto</q-item-section>
        </q-item>

        <q-item clickable v-ripple @click="go('/products-list')">
          <q-item-section avatar><q-icon name="list" /></q-item-section>
          <q-item-section>Mostrar productos</q-item-section>
        </q-item>

        <q-item clickable v-ripple @click="go('/product-by-id')">
          <q-item-section avatar><q-icon name="search" /></q-item-section>
          <q-item-section>Producto por ID</q-item-section>
        </q-item>

        <q-separator />

        <q-item clickable v-ripple @click="go('/purchase')">
          <q-item-section avatar><q-icon name="shopping_cart" /></q-item-section>
          <q-item-section>Crear compra</q-item-section>
        </q-item>

        <q-item clickable v-ripple @click="go('/update-stock')">
          <q-item-section avatar><q-icon name="inventory_2" /></q-item-section>
          <q-item-section>Actualizar stock</q-item-section>
        </q-item>

        <q-item clickable v-ripple @click="go('/inventory')">
          <q-item-section avatar><q-icon name="analytics" /></q-item-section>
          <q-item-section>Ver inventario</q-item-section>
        </q-item>

        <q-item clickable v-ripple @click="go('/inventory-list')">
          <q-item-section avatar><q-icon name="table_chart" /></q-item-section>
          <q-item-section>Lista Inventario</q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <!-- CONTENT -->
    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const leftDrawerOpen = ref(true)
const router = useRouter()

const toggleLeftDrawer = () => {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

const go = (path) => {
  router.push(path)
}
</script>
