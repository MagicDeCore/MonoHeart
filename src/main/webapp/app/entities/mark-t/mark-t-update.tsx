import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITypeT } from 'app/shared/model/type-t.model';
import { getEntities as getTypeTs } from 'app/entities/type-t/type-t.reducer';
import { getEntity, updateEntity, createEntity, reset } from './mark-t.reducer';
import { IMarkT } from 'app/shared/model/mark-t.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMarkTUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMarkTUpdateState {
  isNew: boolean;
  idstypeT: any[];
}

export class MarkTUpdate extends React.Component<IMarkTUpdateProps, IMarkTUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idstypeT: [],
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getTypeTs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { markTEntity } = this.props;
      const entity = {
        ...markTEntity,
        ...values,
        typeTS: mapIdList(values.typeTS)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/mark-t');
  };

  render() {
    const { markTEntity, typeTS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="monoHeartApp.markT.home.createOrEditLabel">Create or edit a MarkT</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : markTEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="mark-t-id">ID</Label>
                    <AvInput id="mark-t-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="mark-t-name">
                    Name
                  </Label>
                  <AvField id="mark-t-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="mark-t-typeT">Type T</Label>
                  <AvInput
                    id="mark-t-typeT"
                    type="select"
                    multiple
                    className="form-control"
                    name="typeTS"
                    value={markTEntity.typeTS && markTEntity.typeTS.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {typeTS
                      ? typeTS.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/mark-t" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  typeTS: storeState.typeT.entities,
  markTEntity: storeState.markT.entity,
  loading: storeState.markT.loading,
  updating: storeState.markT.updating,
  updateSuccess: storeState.markT.updateSuccess
});

const mapDispatchToProps = {
  getTypeTs,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MarkTUpdate);
