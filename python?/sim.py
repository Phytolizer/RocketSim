earth_mass=6*10**24 # constant
timestep = 0.001
G = 6.67*10**(-11) # constant
R=6.4*10**6 # constant
r=R
alt = r - R
payload=12000 # constant
fuel1=28000
tank1=2000
fuel2=100 # change this pls
total_mass = payload + fuel1 + tank1 + fuel2
v_ex1 = 5000
v_ex2 = 7500
time = 0
velocity = 0
accel = 0
segment = 1
liftoff_time=0

def getfuelrate():
    if fuel1 > 0:
        if time<10:
            return 20 * time
        else:
            return 200
    else:
        segment = 2
        if fuel2 > 0:



def getthrustforce():
    if segment == 1:
        global fuel1
        rate = getfuelrate()
        fuel1 -= getfuelrate() * timestep
        return rate * v_ex1
    else:
        global fuel2
        rate = getfuelrate()
        fuel2 -= rate * timestep
        return rate * v_ex2

def gravforce():
    return (G * total_mass * earth_mass) / (r ** 2)

def dragforce():
    global alt
    b=0
    if altitude<200000:
        b=100-0.0005*altitude
    return -b*velocity

def kinematics():
    global velocity
    global r
    global total_mass
    global alt
    global accel
    global liftoff_time
    total_mass = payload + fuel
    f_net=getthrustforce() - gravforce() + dragforce()
    if f_net > 0 and liftofftime == 0:
        liftofftime = time
    if f_net < 0 and altitude < 100:
        f_net = 0
    accel = f_net / total_mass
    r += velocity * timestep + .5*accel*timestep**2
    velocity = velocity + accel * timestep
    altitude = r-R

while time < 300:
    global time
    print('velocity: ' + str(velocity))
    print('time: ' + str(time))
    print('altitude: ' + str(alt))
    kinematics()
    time += timestep

print(G)