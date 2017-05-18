import math


class Vector:
    """A numerical construct with only direction and magnitude."""

    def __init__(self, x, y):
        polar_coords = to_polar(x, y)
        self.r = polar_coords[0]
        self.theta = polar_coords[1]
        keepinbounds(self.theta)

    def w(self):
        return to_rectangular(self.r, self.theta)[0]

    def h(self):
        return to_rectangular(self.r, self.theta)[1]

    def rotate(self, amount):
        self.theta += amount
        keepinbounds(self.theta)

    def stretch(self, amount):
        self.r += amount


class MovableVector(Vector):
    """A vector with coordinates, a direction, and magnitude."""

    def __init__(self, x, y, w, h):
        super().__init__(x, y)
        self.x = x
        self.y = y

    def x(self):
        return self.x

    def y(self):
        return self.y

    def shift(self, x, y):
        self.x += x
        self.y += y


def to_polar(x, y):
    r = math.sqrt(x ** 2 + y ** 2)  # pythagorean theorem
    theta = math.atan(y / x)  # get angle from triangle legs
    return [r, theta]


def to_rectangular(r, theta):
    x = r * math.cos(theta)
    y = r * math.sin(theta)
    return [x, y]


def keepinbounds(theta):  # keeps theta between 0 and 2pi
    while theta < 0:
        theta += 2 * math.pi
    while theta > 2 * math.pi:
        theta -= 2 * math.pi
    return theta


def add(v1, v2):
    if type(v1) != type(v2) != type(Vector):
        raise ValueError("Both arguments must be vectors!")
    w = v1.w() + v2.w()
    h = v1.h() + v2.h()
    return Vector(w, h)
