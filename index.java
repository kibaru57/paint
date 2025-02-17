import 'package:flutter/material.dart';
import 'package:flutter_colorpicker/flutter_colorpicker.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  MobileAds.instance.initialize(); // Initialize ads
  runApp(PaintApp());
}

class PaintApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Paint App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: PaintScreen(),
    );
  }
}

class PaintScreen extends StatefulWidget {
  @override
  _PaintScreenState createState() => _PaintScreenState();
}

class _PaintScreenState extends State<PaintScreen> {
  List<DrawingPoint> drawingPoints = [];
  Color selectedColor = Colors.black;
  double strokeWidth = 5.0;
  BannerAd? _bannerAd;

  @override
  void initState() {
    super.initState();
    _loadBannerAd();
  }<script type="text/javascript">
	atOptions = {
		'key' : '57e44be46b91661463e308b7cbb26c45',
		'format' : 'iframe',
		'height' : 300,
		'width' : 160,
		'params' : {}
	};
</script>
<script type="text/javascript" src="//www.highperformanceformat.com/57e44be46b91661463e308b7cbb26c45/invoke.js"></script>
  
          print('Ad loaded.');
        },
        onAdFailedToLoad: (Ad ad, LoadAdError error) {
          print('Ad failed to load: $error');
        },
      ),
    )..load();
  }

  void _showColorPicker() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('Pick a color'),
        content: SingleChildScrollView(
          child: BlockPicker(
            pickerColor: selectedColor,
            onColorChanged: (color) {
              setState(() {
                selectedColor = color;
              });
            },
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: Text('OK'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Paint App'),
        actions: [
          IconButton(
            icon: Icon(Icons.color_lens),
            onPressed: _showColorPicker,
          ),
          Slider(
            value: strokeWidth,
            min: 1,
            max: 20,
            onChanged: (value) {
              setState(() {
                strokeWidth = value;
              });
            },
          ),
        ],
      ),
      body: Row(
        children: [
          if (_bannerAd != null)
            Container(
              width: 50,
              child: AdWidget(ad: _bannerAd!),
            ),
          Expanded(
            child: GestureDetector(
              onPanUpdate: (details) {
                setState(() {
                  drawingPoints.add(
                    DrawingPoint(
                      position: details.localPosition,
                      color: selectedColor,
                      strokeWidth: strokeWidth,
                    ),
                  );
                });
              },
              onPanStart: (details) {
                setState(() {
                  drawingPoints.add(
                    DrawingPoint(
                      position: details.localPosition,
                      color: selectedColor,
                      strokeWidth: strokeWidth,
                    ),
                  );
                });
              },
              onPanEnd: (details) {
                setState(() {
                  drawingPoints.add(DrawingPoint());
                });
              },
              child: CustomPaint(
                painter: DrawingPainter(drawingPoints),
                child: Container(),
              ),
            ),
          ),
          if (_bannerAd != null)
            Container(
              width: 50,
              child: AdWidget(ad: _bannerAd!),
            ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.clear),
        onPressed: () {
          setState(() {
            drawingPoints.clear();
          });
        },
      ),
    );
  }
}

class DrawingPainter extends CustomPainter {
  final List<DrawingPoint> drawingPoints;

  DrawingPainter(this.drawingPoints);

  @override
  void paint(Canvas canvas, Size size) {
    for (var point in drawingPoints) {
      if (point.position != null) {
        final paint = Paint()
          ..color = point.color
          ..strokeWidth = point.strokeWidth
          ..strokeCap = StrokeCap.round;

        canvas.drawPoints(PointMode.points, [point.position!], paint);
      }
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}

class DrawingPoint {
  Offset? position;
  Color color;
  double strokeWidth;

  DrawingPoint({
    this.position,
    this.color = Colors.black,
    this.strokeWidth = 5.0,
  });
}
