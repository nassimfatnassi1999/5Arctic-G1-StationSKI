provider "azurerm" {
  features {}
  tenant_id = "604f1a96-cbe8-43f8-abbf-f8eaf5d85730"
  subscription_id = "e7653c67-ca4d-4bc9-95fb-9862b85802f8"
}

resource "azurerm_resource_group" "myterraformgroup" {
  name     = "myResourceGroup"
  location = "eastus"
}

resource "azurerm_kubernetes_cluster" "myakscluster" {
  name                = "myAKSCluster"
  location            = azurerm_resource_group.myterraformgroup.location
  resource_group_name = azurerm_resource_group.myterraformgroup.name
  dns_prefix          = "myaksdns"

  default_node_pool {
    name            = "default"
    node_count      = 2
    vm_size         = "Standard_DS2_v2"
    os_disk_size_gb = 30
  }

  identity {
    type = "SystemAssigned"
  }

  tags = {
    environment = "Terraform Demo"
  }
}