package ma.ac.ensa.ebankingapi.authorizations;

import ma.ac.ensa.ebankingapi.exception.UnauthorizedException;

public abstract class Authorization<T> {

    public void can(String permission, T entity) throws UnauthorizedException {
        Boolean result = false;

        switch (permission) {
            case "update": result = update(entity);
                break;
            case "create": result = create();
                break;
            case "delete": result = delete(entity);
                break;
            case "view":  result = view(entity);
                break;
            case "viewAll": result = viewAll();
                break;
            case "viewSomeOfEntity": result = viewSomeOfEntity(entity);
                break;
            default: throw new IllegalStateException(String.format("%s permission is not defined.", permission));
        }

        if (! result) {
            throw new UnauthorizedException(String.format("You are not authorized to %s %s entity.", permission, entity.getClass().getName()));
        }
    }

    public void can(String permission) throws UnauthorizedException {
        can(permission, null);
    }

    public abstract Boolean create();

    public abstract Boolean update(T entity);

    public abstract Boolean delete(T entity);

    public abstract Boolean viewAll();

    public abstract Boolean view(T entity);

    public abstract Boolean viewSomeOfEntity(T entity);

}
