import 'package:flutter/material.dart';

import '../fragments/wishlist/details_wishlist.dart';
import '../fragments/wishlist/wishlist.dart';

class Wishlist extends StatefulWidget {
  const Wishlist({super.key});

  @override
  WishlistState createState() => WishlistState();
}

GlobalKey<NavigatorState> wishListNavigatorKey = GlobalKey<NavigatorState>();

class WishlistState extends State<Wishlist> {
  @override
  Widget build(BuildContext context) {
    return Navigator(
      key: wishListNavigatorKey,
      onGenerateRoute: (RouteSettings settings) {
        return MaterialPageRoute(
            settings: settings,
            builder: (BuildContext context) {
              if (settings.name == "/detailsWishlist") {
                return const DetailsWishlistView();
              }
              return const WishlistView();
            });
      },
    );
  }
}