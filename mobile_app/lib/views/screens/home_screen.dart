import 'package:animated_background/animated_background.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key, required this.title});

  final String title;

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _HomePage();
  }}

class _HomePage extends State<HomePage> with TickerProviderStateMixin {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),

      body: AnimatedBackground(
          child: Center(child: Text("Hello World "),),
          vsync: this,
          behaviour: RandomParticleBehaviour(
            options: const ParticleOptions(
              spawnMaxRadius: 18,
              spawnMaxSpeed: 30, // Giá trị sửa
              particleCount: 18,
              spawnMinSpeed: 10, // Giá trị sửa
              minOpacity: 0.3,
              spawnOpacity: 0.4,
              baseColor: Colors.blue,
              image: Image(image: AssetImage('assets/snowflake.png')),
            ),
          ),
      ),

    );
  }

} 