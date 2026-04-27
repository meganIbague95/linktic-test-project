const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: 'products', component: () => import('pages/ProductsPage.vue') },
      { path: 'products-list', component: () => import('pages/ProductsListPage.vue') },
      { path: 'product-by-id', component: () => import('pages/ProductByIdPage.vue') },
      { path: 'purchase', component: () => import('pages/PurchasePage.vue') },
      { path: 'update-stock', component: () => import('pages/UpdateStockPage.vue') },
      { path: 'inventory', component: () => import('pages/InventoryPage.vue') },
      { path: 'inventory-list', component: () => import('pages/InventoryListPage.vue') },
    ],
  },

  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
]

export default routes
