import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IModuleT, defaultValue } from 'app/shared/model/module-t.model';

export const ACTION_TYPES = {
  FETCH_MODULET_LIST: 'moduleT/FETCH_MODULET_LIST',
  FETCH_MODULET: 'moduleT/FETCH_MODULET',
  CREATE_MODULET: 'moduleT/CREATE_MODULET',
  UPDATE_MODULET: 'moduleT/UPDATE_MODULET',
  DELETE_MODULET: 'moduleT/DELETE_MODULET',
  RESET: 'moduleT/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IModuleT>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ModuleTState = Readonly<typeof initialState>;

// Reducer

export default (state: ModuleTState = initialState, action): ModuleTState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MODULET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MODULET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MODULET):
    case REQUEST(ACTION_TYPES.UPDATE_MODULET):
    case REQUEST(ACTION_TYPES.DELETE_MODULET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MODULET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MODULET):
    case FAILURE(ACTION_TYPES.CREATE_MODULET):
    case FAILURE(ACTION_TYPES.UPDATE_MODULET):
    case FAILURE(ACTION_TYPES.DELETE_MODULET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MODULET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_MODULET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MODULET):
    case SUCCESS(ACTION_TYPES.UPDATE_MODULET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MODULET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/module-ts';

// Actions

export const getEntities: ICrudGetAllAction<IModuleT> = (page, size, sort) => {
  // size = 999;
  // console.log(page + ' '  + size);
  const requestUrl = `${apiUrl}${sort ? `?page=0&size=999&sort=${sort}` : '?page=0&size=999'}`;
  return {
    type: ACTION_TYPES.FETCH_MODULET_LIST,
    payload: axios.get<IModuleT>(`${requestUrl}${sort ? '&' : '&'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IModuleT> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MODULET,
    payload: axios.get<IModuleT>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IModuleT> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MODULET,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IModuleT> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MODULET,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IModuleT> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MODULET,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
