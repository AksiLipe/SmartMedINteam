from django.contrib import admin
from django.urls import path, include

urlpatterns = [
    path("admin/", admin.site.urls),
    path("login/", include("authorization.urls")),
    path("faq/", include("faq.urls")),
    path("table/", include("table.urls")),
]
